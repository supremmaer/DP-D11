<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">
<!-- Listing grid -->


<b><spring:message code="administrator.avgNpU"/></b>
<fmt:formatNumber value="${avgNewspaperperUser}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.sdNpU"/></b>
<fmt:formatNumber value="${sdNewspaperperUser}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.avgApU"/></b>
<fmt:formatNumber value="${avgArticlesperRendezvous}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.sdApU"/></b>
<fmt:formatNumber value="${sdArticlesperRendezvous}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.avgApN"/></b>
<fmt:formatNumber value="${avgArticlesperNewspaper}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.sdApN"/></b>
<fmt:formatNumber value="${sdArticlesperNewspaper}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.NwithMoreA"/></b>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="newspaperWithMoreArticles" requestURI="${requestURI}" id="row">
	
	<b><spring:message code="administrator.NwithMoreA" var="title"/></b>
	<display:column title="${title}">
		<a href="newspaper/display.do?newspaperId=${row.id}">${row.title}</a>
	</display:column>
	
</display:table>
<br/><br/><br/>

<b><spring:message code="administrator.NwithLessA"/></b>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="newspaperWithLessArticles" requestURI="${requestURI}" id="row">
	
	<b><spring:message code="administrator.NwithLessA" var="title"/></b>
	<display:column title="${title}">
		<a href="newspaper/display.do?newspaperId=${row.id}">${row.title}</a>
	</display:column>
	
</display:table>
<br/><br/><br/>

<b><spring:message code="administrator.RatioUwN"/></b>
<fmt:formatNumber value="${ratioUserNewspaperWriters}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.RatioUwA"/></b>
<fmt:formatNumber value="${ratioUserArticlesWriters}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.avgFpA"/></b>
<fmt:formatNumber value="${avgFollowPerArticle}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.avgFpAO"/></b>
<fmt:formatNumber value="${avgFollowPerArticlePublishedOne}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.avgFpAT"/></b>
<fmt:formatNumber value="${avgFollowPerArticlePublishedTwo}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.avgCpU"/></b>
<fmt:formatNumber value="${avgChirpPerUser}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.sdCpU"/></b>
<fmt:formatNumber value="${sdChirpPerUser}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.RatioUmC"/></b>
<fmt:formatNumber value="${ratioUserMoreChirp}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.RatioPvP"/></b>
<fmt:formatNumber value="${ratioPublicVsPrivate}" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.avgApPRN"/></b>
<fmt:formatNumber value="${avgArticlePerPrivateNews}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.avgApPUN"/></b>
<fmt:formatNumber value="${avgArticlePerPublicNews }" pattern="####0.00"/>
<br/><br/><br/>

<b><spring:message code="administrator.RatioSvsC"/></b>
<fmt:formatNumber value="${ratioSubsVSTotal}" pattern="####0.00"/>
<br/>
<b><spring:message code="administrator.avgRPVP"/></b>
<fmt:formatNumber value="${avgRatioPrivateVsPublic}" pattern="####0.00"/>
<br/><br/><br/>

</security:authorize>
	