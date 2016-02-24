SELECT TO_CHAR(create_date, 'YYYY-MM') month_year, COUNT(*) FROM i2b2_user_stats 
WHERE local_user_id NOT IN (SELECT user_id FROM i2b2_user_exclusions)  
GROUP BY TO_CHAR(create_date, 'YYYY-MM') ORDER BY TO_CHAR(create_date, 'YYYY-MM')