package invoicing.web.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("{} {} [{}] - params: {}",
                request.getMethod(),
                request.getRequestURI(),
                handler,
                request.getParameterMap().entrySet().stream()
                    .map((Map.Entry<String, String[]> entry) -> entry.getKey() + ": " + Arrays.toString(entry.getValue()))
                    .collect(Collectors.joining(", "))
        );
        return true;
    }

}
