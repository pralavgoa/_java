SELECT TO_CHAR(create_date, 'YYYY-MM') month_year, COUNT(*) total_count 
FROM i2b2_user_stats 
WHERE create_date >= TO_DATE('10/01/2013', 'MM/DD/YYYY') 
GROUP BY TO_CHAR(create_date, 'YYYY-MM') 
ORDER BY TO_CHAR(create_date, 'YYYY-MM')