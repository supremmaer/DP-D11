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

<spring:message code="volume.title" />: <jstl:out value="${volume.title}"></jstl:out> <br>
<spring:message code="volume.description" />: <jstl:out value="${volume.description}"></jstl:out> <br>
<spring:message code="volume.year" />: <jstl:out value="${volume.year}"></jstl:out>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="volume.newspapers" requestURI="${requestURI }" id="row">
	
		<spring:message code="newspaper.title" var="titleHeader" />
	<display:column title="${titleHeader}">
		<a href="newspaper/display.do?newspaperId=${row.id}"><jstl:out
				value="${row.title}"></jstl:out></a>
	</display:column>
	
		<spring:message code="newspaper.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />
	
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

	
	</display:table>
	</br>
<security:authorize access="hasRole('USER')">
	<jstl:if test="${userId!=null}">
		<a href="user/volume/addNewspaper.do?volumeId=${volume.id}"><spring:message code="volume.addNewspaper"/></a>
	</jstl:if>
</security:authorize>