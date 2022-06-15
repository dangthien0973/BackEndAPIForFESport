package com.example.ApiDoAn.until;


import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SiteUrlUtils {
    public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        System.out.println(siteURL.replace(request.getServletPath(), ""));
        return siteURL.replace(request.getServletPath(), "");
    }
}
