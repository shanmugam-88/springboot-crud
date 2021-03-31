package org.learn.curd.logging.filter;


import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationFilter implements Filter,MessageHeaderConstants {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            MDC.put(CORRELATION_ID_LOG,getCorrelationId(request));
            MDC.put(IPADDRESS_LOG,request.getRemoteAddr());
        }
        try{
            filterChain.doFilter(servletRequest,servletResponse);
        } finally {
            MDC.remove(CORRELATION_ID_LOG);
            MDC.remove(IPADDRESS_LOG);
            MDC.clear();
        }
    }

    @Override
    public void destroy() {

    }

    private String getCorrelationId(final HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if(StringUtils.isEmpty(correlationId)) {
            correlationId = UUID.randomUUID().toString();
        }
        return correlationId;
    }
}
