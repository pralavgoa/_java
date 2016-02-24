<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>UCReX Usage Statistics</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
      }
    </style>

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="images/favicon.ico">
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png">
  </head>
  <body style="overflow:scroll;">
    <div class="topbar">
      <div class="topbar-inner">
        <div class="container-fluid">
          <a class="brand" href="#">UCReX Usage Statistics</a>
          <ul class="nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
          </ul>
          <p class="pull-right">Logged in as <a href="#">username</a></p>
        </div>
      </div>
    </div>
    <div class="container-fluid">
      <div class="sidebar">
        <div class="well">
          <h5>Charts</h5>
          <ul>
            <li><a href="#chart_1">Total Users and Queries per month</a></li>
			<li><a href="#chart_2">Total Queries and Failed Queries per month</a></li>
            <li><a href="#chart_3">Percentage Failed Queries per month</a></li>
          </ul>
          <h5>Tables</h5>
          <ul>
            <li><a href="download">Number of observations (Download)</a></li>
            <li><a href="#table_1">Number of observations (Total)</a></li>
            <li><a href="#">Link</a></li>
            <li><a href="#">Link</a></li>
            <li><a href="#">Link</a></li>
            <li><a href="#">Link</a></li>
          </ul>
        </div>
      </div>
      <div class="content">
        <div class="row">
		  <div class="span12">
		  	<div>
				<jsp:include page="UserAndQueryStats.jsp" />
			</div>
		  </div>
        </div>
        <hr />
        <div class="row">
		  <div class="span12">
		  	<div>
				<jsp:include page="TotalAndFailureStats.jsp" />
			</div>
		  </div>
        </div>
        <hr />
        <div class="row">
		  <div class="span12">
		  	<div>
				<jsp:include page="PercentageFailedQueries.jsp" />
			</div>
		  </div>
        </div>
        <hr />
        <div class="row">
		  <div class="span12">
		  	<div>
				<jsp:include page="NumberOfObservations.jsp" />
			</div>
		  </div>
        </div>
        <!-- Example row of columns -->
        <footer>
          <p>&copy; University Of California Los Angeles - 2014</p>
        </footer>
      </div>
    </div>
  </body>
</html>