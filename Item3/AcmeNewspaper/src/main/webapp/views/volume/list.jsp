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

<display:table pagesize="5" class="displaytag" 
	name="volumes" requestURI="${requestURI }" id="row">
	
		<spring:message code="volume.title" var="titleHeader" />
	<display:column title="${titleHeader}">
		<a href="volume/display.do?volumeId=${row.id }"><jstl:out
				value="${row.title}"></jstl:out></a>
	</display:column>
	
		<spring:message code="volume.description" var="descriptionHeader" />
	<display:column title="${descriptionHeader}">
		<jstl:out
				value="${row.description}"></jstl:out>
	</display:column>
	
	<spring:message code="volume.year" var="yearHeader" />
	<display:column title="${yearHeader}">
		<jstl:out
				value="${row.year}"></jstl:out>
	</display:column>
	
	<security:authorize access="hasRole('USER')">
		<display:column>
			<jstl:if test="${userId== row.user.id}">
				<a href="user/volume/edit.do?volumeId=${row.id}"><spring:message code="volume.edit"/></a>
			</jstl:if>	
			
		</display:column>
	</security:authorize>
	
	
	
</display:table>

<security:authorize access="hasRole('USER')">
	<a href="user/volume/create.do"><spring:message code="volume.create"/></a>
</security:authorize>