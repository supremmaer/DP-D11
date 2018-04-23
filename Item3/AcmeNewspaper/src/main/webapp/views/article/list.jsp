<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<!-- search  -->

<form action="article/list.do" method="get">

	<input name="keyword" />
	<br />

	<input type="submit"
		value="search" />

</form>


<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" name="articles" requestURI="${requestURI}" id="row">

<spring:message code="article.title" var="title"/>
<display:column title="${title}" sortable="false">
	<a href="article/display.do?articleId=${row.id}"><jstl:out value="${row.title}"/></a>
</display:column>

<spring:message code="article.summary" var="summary"/>
<display:column property="summary" title="${summary}" sortable="false"/>
 
<spring:message code="master.page.date.format" var="datePattern"/>
<spring:message code="article.publishMoment" var="moment"/>
<display:column property="publishMoment" title="${moment}" sortable="false" format="{0,date,${datePattern}}"/>

<security:authorize access="hasRole('USER')">
	
	<spring:message code="article.title" var="title"/>
	<display:column sortable="false">
		<jstl:if test="${userId!=null && row.draftMode==true && row.publishMoment==null}">
		<a href="user/article/edit.do?&articleId=${row.id}"><spring:message code="article.edit"/></a>
		</jstl:if>		
	</display:column>

</security:authorize>
<security:authorize access="hasRole('ADMIN')">
	<display:column>
	<a href="administrator/article/delete.do?articleId=${row.id }"><spring:message code="chirp.delete" /></a>
	</display:column>
	</security:authorize>

</display:table>

