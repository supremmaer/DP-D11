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

<form:form action="${requestURI}"	modelAttribute="advertisement">

<form:hidden path="id"/>
<form:hidden path="version"/>
<form:hidden path="newspaper"/>
<form:hidden path="taboo"/>
<form:hidden path="agent"/>

<form:label path="title">
		<spring:message code="advertisement.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	
<form:label path="banner">
		<spring:message code="advertisement.banner" />:
	</form:label>
	<form:input path="banner" />
	<form:errors cssClass="error" path="banner" />
	<br />
	
<form:label path="targetPage">
		<spring:message code="advertisement.targetPage" />:
	</form:label>
	<form:input path="targetPage" />
	<form:errors cssClass="error" path="targetPage" />
	<br />
	
	<form:label path="creditCard"><spring:message code="advertisement.creditcard" /></form:label>
	<form:select path="creditCard">
		<form:option
			label="-----"
			value="0"
			/>
		<form:options
			items="${creditcards}"
			itemLabel="number"
			itemValue="id"
		/>
	</form:select>

<input type="submit" name="save"
		value="<spring:message code="advertisement.save" />" />
		 
	<spring:message code="advertisement.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('newspaper/list.do');" />
	<br />	
	
</form:form>

