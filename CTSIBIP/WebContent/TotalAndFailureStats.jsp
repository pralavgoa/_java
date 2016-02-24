<%@page import="ucrex.usage.statistics.UCRexStats"%>
<%@page import="ucrex.usage.statistics.db.DataBank"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
DataBank db = UCRexStats.getInstance().getDatabank();
db.open();
String data = db.getSuccessAndFailureStats().successAndFailureQueriesAsString();
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
          vAxis: {title: 'Queries'},
		  seriesType: "bars",
		  series:{0:{type:"line"},1:{type:"line"}}
        };

        var chart = new google.visualization.ComboChart(document.getElementById('chart_2'));
        chart.draw(data, options);
      }
    </script>
    <div>
    	<h2>Total and failed queries</h2>
    </div>
	<div id="chart_2" style="width: 900px; height: 500px;"></div>