package cn.cherish.springcloud.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class CsrfSecurityRequestMatcher implements RequestMatcher {

    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
    /**
     * 需要排除的url列表
     */
    private List<String> excludeUrls;

    @Override
    public boolean matches(HttpServletRequest request) {
        if (excludeUrls != null && excludeUrls.size() > 0) {
            String servletPath = request.getServletPath();
            for (String url : excludeUrls) {
                if (servletPath.contains(url)) {
                    log.info("【排除的路径】 url: {}", servletPath);
                    return false;
                }
            }
        }
        return !allowedMethods.matcher(request.getMethod()).matches();
    }

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }


}