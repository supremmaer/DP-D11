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



<!-- search  -->

<form action="newspaper/list.do" method="get">





	<input name="criteria" /> <br /> <input type="submit" value="search" />

</form>


<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="newspapers" requestURI="newspaper/list.do" id="row">

	<spring:message code="newspaper.title" var="titleHeader" />
	<display:column title="${titleHeader}">
		<a href="newspaper/display.do?newspaperId=${row.id}"><jstl:out
				value="${row.title}"></jstl:out></a>
	</display:column>

	<spring:message code="newspaper.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />

	<spring:message code="newspaper.publicationDate"
		var="publicationDateHeader" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="publicationDate"
		format="{0,date,${dateFormat}}" title="${publicationDateHeader}" />

	<spring:message code="newspaper.publicity" var="publicityHeader" />
	<display:column title="${publicityHeader}">

		<jstl:if test="${row.publicity=='true'}">
			<spring:message code="newspaper.private" />
		</jstl:if>
		<jstl:if test="${row.publicity=='false'}">
			<spring:message code="newspaper.public" />
		</jstl:if>

	</display:column>

	<spring:message code="newspaper.picture" var="pictureHeader" />
	<display:column title="${pictureHeader}">
		<img src="${row.picture}" height="150" width="250" />
	</display:column>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column sortable="false">
			<jstl:if test="${row.publicity==true && !newspaperscustomer.contains(row)}">
				<a href="customer/subscribe.do?newspaperId=${row.id}"><spring:message
						code="newspaper.subscribe" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="administrator/newspaper/delete.do?newspaperId=${row.id}"><spring:message
					code="chirp.delete" /></a>
		</display:column>
	</security:authorize>
</display:table>

