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

	<!-- Guardamos en una variable el formato de la fecha  -->
	

	<spring:message code="master.page.date.format" var="dateFormat" />
	
	
 	<fmt:setLocale value="${locale}"/>
	
	
<div id="newspaper">
<!-- 	<h1>
		<jstl:out value="${newspaper.getTitle()}" />
	</h1> -->
	<br />

	<ul style="list-style-type: disc">

		<li><b><spring:message code="newspaper.title"></spring:message>:</b>
			<jstl:out value="${newspaper.getTitle()}" /></li>

		<li><b><spring:message code="newspaper.description"></spring:message>:</b>
			<jstl:out value="${newspaper.getDescription()}" /></li>

		<li><b><spring:message code="newspaper.publicationDate"></spring:message>:</b> 
			<fmt:formatDate value="${newspaper.getPublicationDate()}" pattern="${dateFormat}" /></li>
	
		<li><b><spring:message code="newspaper.publicity"/>:</b>
			<jstl:if test="${newspaper.getPublicity()=='true'}"><spring:message code="newspaper.private" /></jstl:if>
			<jstl:if test="${newspaper.getPublicity()=='false'}"><spring:message code="newspaper.public" /></jstl:if>
		</li>
	<jstl:if test="${!mostrarArticles}"><spring:message code="newspaper.nosub" /></jstl:if>
	
	<jstl:if test="${mostrarArticles}">
		<li><b><spring:message code="newspaper.picture"></spring:message>:</b>
				<jstl:out value="${newspaper.getPicture()}"/>
				</br>
				<img src="${newspaper.getPicture()}" height="150" width="250" />
			</li>
			
			<li><b><spring:message code="newspaper.articles"></spring:message>:</b>
			<jstl:forEach items="${newspaper.articles}" var="cat">
			</br>
					<a href="article/display.do?articleId=${cat.id}" ><jstl:out value="${cat.title}"></jstl:out></a>
					</br>
					<a href="user/display.do?userId=${mapaMegaComplejo[cat.id].id}" ><jstl:out value="${mapaMegaComplejo[cat.id].name}"></jstl:out></a>
					<a href="user/display.do?userId=${mapaMegaComplejo[cat.id].id}" ><jstl:out value="${mapaMegaComplejo[cat.id].surname}"></jstl:out></a>
					
					</br>
				
				
					<jstl:out value="${cat.summary}"></jstl:out>
					</br>
					<jstl:out value="${cat.text}"></jstl:out>
					<jstl:forEach items="${cat.pictures}" var="image">
					</br>
					<jstl:out value="${image}"></jstl:out>
					<img src="${image}" height="150" width="250" />
					</jstl:forEach>
			</jstl:forEach>
				<jstl:if test="${newspaper.publicationDate==null}">
					<br />
					<br />
					<security:authorize access="hasRole('USER')">
						<a href="user/article/create.do?newspaperId=${newspaper.id}" ><spring:message code="article.create"/></a>
					</security:authorize>
				</jstl:if>
			</li>
	</ul>
	</jstl:if>

</div>


<jstl:if test="${EsAutor}">
					<a href="newspaper/publish.do?newspaperId=${newspaper.id}" ><spring:message code="newspaper.publish"/></a>

</jstl:if>
<jstl:if test="${Error}">
	<spring:message code="newspaper.error"/>
</jstl:if>
</br></br>
<input type="button" name="cancel"
	value="<spring:message code="newspaper.back" />"
	onclick="javascript: relativeRedir('newspaper/list.do')" />
	
