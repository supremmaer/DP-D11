<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Sample Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		<li><a class="fNiv"><spring:message code="master.page.Newspaper" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="newspaper/list.do"><spring:message
							code="master.page.NewspaperList" /></a></li>
				<security:authorize access="hasRole('USER')">
				<li><a href="newspaper/create.do"><spring:message
							code="master.page.NewspaperCreate" /></a></li>
				</security:authorize>
			</ul></li>
		
		<li><a class="fNiv"><spring:message code="master.page.Article" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="article/list.do"><spring:message
							code="master.page.ArticleList" /></a>
				</li>
				<security:authorize access="hasRole('USER')">
					<li><a href="user/article/list.do"><spring:message
								code="master.page.myArticles" /></a>
					</li>
				</security:authorize>
			</ul>
			
		</li>
		
		<li><a class="fNiv"><spring:message code="master.page.User" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="user/list.do"><spring:message
							code="master.page.UserList" /></a></li>
				
			</ul>
		</li>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		
		
		<li><a href="actor/create.do?actorType=USER"><spring:message
						code="master.page.registerUser" /></a></li>
						
		<li><a href="actor/create.do?actorType=CUSTOMER"><spring:message
					code="master.page.registerCustomer" /></a></li>
		
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('ADMIN')">
					<li><a href="administrator/chirp/list.do"><spring:message code="master.page.administrator.chirps" /></a></li>
					<li><a href="administrator/newspaper/list.do"><spring:message code="master.page.administrator.newspaper" /></a></li>
					<li><a href="administrator/article/list.do"><spring:message code="master.page.administrator.articles" /></a></li>
					<li><a href="administrator/config/display.do"><spring:message code="master.page.administrator.tabooWords" /></a></li>
					<li><a href="actor/create.do?actorType=ADMIN"><spring:message
					code="master.page.createAdmin" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message
								code="master.page.administrator.dashboard" /></a></li>
					</security:authorize>		
					<security:authorize access="hasRole('USER')">
					<li><a href="user/chirp/list.do"><spring:message code="master.page.user.chirps.followed" /></a></li>
					<li><a href="user/chirp/create.do"><spring:message code="chirp.create" /></a></li>
					</security:authorize>		
					<security:authorize access="hasRole('CUSTOMER')">
					<li><a href="customer/creditCard/create.do"><spring:message code="master.page.creditcard.create" /></a></li>
					</security:authorize>	
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

