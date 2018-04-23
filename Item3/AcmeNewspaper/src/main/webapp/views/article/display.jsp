<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>	

<spring:message code="master.page.date.format" var="dateFormat" />	
<fmt:setLocale value="${locale}"/>
	
<div id="article">

	<br />
	
	<a href="user/display.do?userId=${user.id}" ><spring:message code="article.Autor"/></a>

	<br />
	<br />
	
	<b><spring:message code="article.title"/>:</b>
	<jstl:out value="${article.title}" />
	<br />
	
	<b><spring:message code="article.publishMoment"/>:</b> 
	<fmt:formatDate value="${article.publishMoment}" pattern="${dateFormat}" />
	<br />
	<br />
	
	<b><spring:message code="article.summary"/>:</b>
	<jstl:out value="${article.summary}" />
	<br />
	<br />
	<jstl:if test="${privateNewspaper || userId!=null || subscribed}">
	<b><spring:message code="article.text"/>:</b>
	<jstl:out value="${article.text}" />
	<br />
	
	
	<jstl:forEach items="${article.pictures}" var="current">
		<img style="max-width: 20vw;" src="${current}" alt="${current}" />
		<br />
	</jstl:forEach>
	<br />
	</jstl:if>
</div>
<jstl:if test="${privateNewspaper==true || userId!=null || subscribed}">
<jstl:forEach items="${followups}" var="step">
<div style="margin-left: 6%;" class="followup">

	<br />




	
	<b><spring:message code="article.title"/>:</b>
	<jstl:out value="${step.title}" />
	<br />
	
	<b><spring:message code="article.publishMoment"/>:</b> 
	<fmt:formatDate value="${step.publishMoment}" pattern="${dateFormat}" />
	<br />
	<br />
	
	<b><spring:message code="article.summary"/>:</b>
	<jstl:out value="${step.summary}" />
	<br />
	<br />
	
	<b><spring:message code="article.text"/>:</b>
	<jstl:out value="${step.text}" />
	<br />
	
	
	<jstl:forEach items="${step.pictures}" var="current">
		<img style="max-width: 20vw;" src="${current}" alt="${current}" />
		<br />
	</jstl:forEach>
	<br />
</div>
</jstl:forEach>
</jstl:if>
<a href="newspaper/display.do?newspaperId=${newspaperId}"><spring:message code="article.backToNewspaper"/></a>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
<jstl:if test="${user.id==userId && article.publishMoment!=null}">

<a href="user/followup/create.do?articleId=${article.id}"><spring:message code="article.followup"/></a>

</jstl:if>
