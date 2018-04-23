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

<display:table pagesize="5" class="displaytag" name="config.tabooWords"  requestURI="${requestURI}" id="row">

	<spring:message code="config.taboo" var="titleHeader" />
	<display:column title="${titleHeader}"><jstl:out value="${row}"></jstl:out></display:column>
	
	<display:column ><a href="administrator/config/removeTaboo.do?tabooWord=${row }"><spring:message code="config.taboo.remove" /></a></display:column>
	
	

</display:table>

<a href="administrator/config/addTaboo.do"><spring:message code="config.taboo.add"></spring:message></a>



