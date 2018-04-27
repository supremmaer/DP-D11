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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>






<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="volumes" requestURI="volume/list.do" id="row">
	
		<spring:message code="volume.title" var="titleHeader" />
	<display:column title="${titleHeader}">
		<a href="newspaper/list.do"><jstl:out
				value="${row.title}"></jstl:out></a>
	</display:column>
	
		<spring:message code="volume.description" var="descriptionHeader" />
	<display:column title="${descriptionHeader}">
		<jstl:out
				value="${row.title}"></jstl:out>
	</display:column>
	
	<spring:message code="volume.year" var="yearHeader" />
	<display:column title="${yearHeader}">
		<jstl:out
				value="${row.year}"></jstl:out>
	</display:column>

	
	
	
</display:table>

