

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

<form:form action="customer/subscribe.do?newspaperId=${newspaperId}" modelAttribute="subscribeForm">
	
	<form:hidden path="newspaper" />
	
	
	<form:label path="creditCard"><spring:message code="subcribe.creditcard" /></form:label>
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
	
	<acme:submit name="save" code="customer.save"/>
	
	<acme:cancel url="" code="customer.cancel"/>

</form:form>