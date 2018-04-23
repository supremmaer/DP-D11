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





<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" name="chirps"  requestURI="${requestURI}" id="row">

	<spring:message code="chirp.title" var="titleHeader" />
	<display:column title="${titleHeader}"><jstl:out value="${row.title}"></jstl:out></display:column>
	
	<spring:message code="chirp.description" var="descriptionHeader" />
	<display:column title="${descriptionHeader}"><jstl:out value="${row.description}"></jstl:out></display:column>
	
	<spring:message code="chirp.moment" var="momentHeader" />
	<display:column title="${momentHeader}"><jstl:out value="${row.moment}"></jstl:out></display:column>
	
	<security:authorize access="hasRole('ADMIN')">
	<display:column>
	<a href="administrator/chirp/delete.do?chirpId=${row.id }"><spring:message code="chirp.delete" /></a>
	</display:column>
	</security:authorize>
	

</display:table>
</br>
<security:authorize access="hasRole('USER')"><a href="user/chirp/create.do"><spring:message code="chirp.create" /></a></security:authorize>

