<%@page import="ucrex.usage.statistics.UCRexStats"%>
<%@page import="ucrex.usage.statistics.db.DataBank"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
DataBank db = UCRexStats.getInstance().getDatabank();
db.open();
String data = db.getSuccessAndFailureStats().percentageOfFailedQueriesAsString();
db.close();
%>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          <%=data%>
        ]);

        var options = {
          title: 'UC Rex Statistics',
          hAxis: {title: 'Month',  titleTextStyle: {color: 'red'}},
        };

        var chart = new google.visualization.ColumnChart(document.getElementById('chart_3'));
        chart.draw(data, options);
      }
    </script>
</head>
<div>
	<h2>Failed queries (as percentage of total queries)</h2>
</div>
<div id="chart_3" style="width: 900px; height: 500px;"></div>
