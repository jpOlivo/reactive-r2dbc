package com.jpolivo.reactive.logger;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.logging.AccessLog;
import reactor.netty.http.server.logging.AccessLogArgProvider;

@Component
public class NettyWebServerAccessLogFactoryCustomizer
		implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

	@Override
	public void customize(NettyReactiveWebServerFactory serverFactory) {

		// https://github.com/reactor/reactor-netty/pull/1357
		serverFactory.addServerCustomizers(new AccessLogCustomizer());

	}

	private static class AccessLogCustomizer implements NettyServerCustomizer {

		private AccessLogCustomizer() {

		}

		static final String CUSTOM_LOG_FORMAT = "{\"timestamp\":\"{}\",\"remote_host\":\"{}\",\"remote_user\":\"{}\",\"requested_url\":\"{}\",\"status_code\":\"{}\",\"content_length\":\"{}\",\"elapsed_time\":\"{}\",\"request_headers\":{\"x-correlation-id\":\"{}\",\"Authorization\":\"{}\"}}";
		static final Function<AccessLogArgProvider, AccessLog> CUSTOM_ACCESS_LOG = args -> AccessLog.create(
				CUSTOM_LOG_FORMAT,
				ZonedDateTime.parse(args.zonedDateTime(), DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z"))
						.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")),
				args.remoteAddress(), args.user(),
				new StringBuilder(args.method()).append(" ").append(args.uri()).append(" ").append(args.protocol())
						.toString(),
				args.status(), args.contentLength(), args.duration(), args.requestHeader("x-correlation-id"),
				args.requestHeader("Authorization"));

		@Override
		public HttpServer apply(HttpServer httpServer) {
			return httpServer.accessLog(CUSTOM_ACCESS_LOG);
		}
	}

}
