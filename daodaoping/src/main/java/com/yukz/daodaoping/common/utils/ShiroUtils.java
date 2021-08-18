package com.yukz.daodaoping.common.utils;

import com.yukz.daodaoping.common.exception.BDException;
import com.yukz.daodaoping.system.domain.UserToken;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;

import com.yukz.daodaoping.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

public class ShiroUtils {
    @Autowired
    private static SessionDAO sessionDAO;

    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }
    public static UserDO getUser() {
        Object object = getSubjct().getPrincipal();
        UserDO userDo = new UserDO();
        try {
            PropertyUtils.copyProperties(userDo,object);
        }catch(Exception ex) {
            throw new BDException("对象转换失败");
        }
        return userDo;
    }
    public static Long getUserId() {
        return getUser().getUserId();
    }
    public static void logout() {
        getSubjct().logout();
    }

    public static List<Principal> getPrinciples() {
        List<Principal> principals = null;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        return principals;
    }
}
