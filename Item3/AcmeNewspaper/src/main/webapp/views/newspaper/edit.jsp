<%--
 * edit.jsp
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


	
	
	<form:form action="newspaper/edit.do" modelAttribute="newspaper">


	
	<form:hidden path="id" />
	<form:hidden path="version" /> 
	<br />
	<acme:textbox code="newspaper.title" path="title"/>
	<br />
	<acme:textarea code="newspaper.description" path="description"/>
	<br />
<%-- 	<spring:message code="newspaper.publicationDate" var="publicationDate"/>
	<form:label path="publicationDate">${publicationDate}</form:label>
	<form:input path="publicationDate" placeholder="dd/mm/yyyy"/>
	<form:errors cssClass="error" path="publicationDate"/>
	<br />
	 --%>
	
	<acme:textbox code="newspaper.picture" path="picture"/>
	
	<form:label path="publicity">
		<spring:message code="newspaper.publicity" />:
	</form:label>
	<form:select path="publicity">
        <option value="">--</option>
        <option value="true"><spring:message code="newspaper.private" /></option>
        <option value="false"><spring:message code="newspaper.public" /></option>
    </form:select>
	
	<acme:submit name="save" code="newspaper.save"/>


<%--  <form:label path="articles">
		<spring:message code="newspaper.articles" />:
	</form:label>
	<form:select id="articles" path="articles">
		<form:option value="0" label="----" />
		
		<jstl:forEach items="${articles}" var="article" >
		<jstl:choose>
			<jstl:when test="${article.id eq articleId}">
				<form:option value="${article.id}" label="${article.title}" selected="true"/>
			</jstl:when>
		
			<jstl:otherwise>
			<form:option value="${article.id}" label="${article.title}" />
			</jstl:otherwise>
		</jstl:choose>
		</jstl:forEach>
		
	</form:select>  --%>
	
	
	<%-- <security:authorize access="hasRole('ADMINISTRATOR')">
	<acme:submit name="delete" code="comment.delete"/>
	</security:authorize> --%>
	
	<acme:cancel url="" code="newspaper.cancel"/>
	
	</form:form>
	
