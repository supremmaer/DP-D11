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

<b><spring:message code="actor.name" /></b>
<jstl:out value="${user.name}" />
<br />
<b><spring:message code="actor.surname" /></b>
<jstl:out value="${user.surname}" />
<br />
<b><spring:message code="actor.email" /></b>
<jstl:out value="${user.emailAddress}" />
<br />
<b><spring:message code="actor.phone" /></b>
<jstl:out value="${user.phoneNumber}" />
<br />
<b><spring:message code="actor.address" /></b>
<jstl:out value="${user.postalAddress}" />
<br />

<security:authorize access="hasRole('USER')">
<jstl:if test="${userId!=user.id}">
<a	href="user/user/add.do?userId=${user.id}">
	<spring:message code="actor.follow"></spring:message>
</a>
</jstl:if>
</security:authorize>

<!--  Listing grid -->

<display:table pagesize="5" class="displaytag" name="articles" requestURI="${requestURI}" id="row">

<!-- Attributes -->

<spring:message code="article.title" var="title"/>
<display:column title="${title}" sortable="false">
	<a href="article/display.do?articleId=${row.id}"><jstl:out value="${row.title}"/></a>
</display:column>

<spring:message code="article.summary" var="summary"/>
<display:column property="summary" title="${summary}" sortable="false"/>
 
<%-- <spring:message code="article.datePattern" var="datePattern"/> --%>
<spring:message code="article.publishMoment" var="moment"/>
<display:column property="publishMoment" title="${moment}" sortable="false" format="${datePattern}"/>

</display:table>

<b><spring:message code ="actor.following"/></b>
<br/>


<!--  Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false" name="following" requestURI="${requestURI}" id="row2">

<spring:message code="actor.name" var="name"/>
<display:column title="${name}" sortable="true">
<jstl:out value="${row2.name}" />
</display:column>
		
<spring:message code="actor.surname" var="surname"/>
<display:column title="${surname}" sortable="false">
<jstl:out value="${row2.surname}" />
</display:column>

<security:authorize access="hasRole('USER')">
<jstl:if test="${userId==user.id}">
<display:column>
	<a	href="user/user/delete.do?userId=${row2.id}">
		<spring:message code="actor.unfollow"></spring:message>
	</a>
</display:column>
</jstl:if>
</security:authorize>

</display:table>

<br/>
<br/>
<b><spring:message code ="actor.followers"/></b>
<br/>

<!--  Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false" name="followers" requestURI="${requestURI}" id="row3">

<spring:message code="actor.name" var="name"/>
<display:column title="${name}" sortable="true">
<jstl:out value="${row3.name}" />
</display:column>

<spring:message code="actor.surname" var="surname"/>
<display:column title="${surname}" sortable="false">
<jstl:out value="${row3.surname}" />
</display:column>

</display:table>

<br/>
<br/>
<b><spring:message code ="actor.chirps"/></b>
<br/>
<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" name="chirps"  requestURI="${requestURI}" id="row4">

	<spring:message code="chirp.title" var="titleHeader" />
	<display:column title="${titleHeader}"><jstl:out value="${row4.title}"></jstl:out></display:column>
	
	<spring:message code="chirp.description" var="descriptionHeader" />
	<display:column title="${descriptionHeader}"><jstl:out value="${row4.description}"></jstl:out></display:column>
	
	<spring:message code="chirp.moment" var="momentHeader" />
	<display:column title="${momentHeader}"><jstl:out value="${row4.moment}"></jstl:out></display:column>
	
	<security:authorize access="hasRole('ADMIN')">
	<display:column>
	<a href="administrator/chirp/delete.do?chirpId=${row4.id }"><spring:message code="chirp.delete" /></a>
	</display:column>
	</security:authorize>
	

</display:table>

