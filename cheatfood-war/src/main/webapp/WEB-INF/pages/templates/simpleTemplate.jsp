<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <t:insertAttribute name="meta" />
    <t:insertAttribute name="scripts" />
</head>

<body>

<t:insertAttribute name="header" />


<t:insertAttribute name="content" />


<t:insertAttribute name="loginModal" />

<t:insertAttribute name="registrationModal" />

<t:insertAttribute name="forgetPasswordModal" />

<t:insertAttribute name="footer" />

<t:insertAttribute name="googleAnalytics" />

</body>