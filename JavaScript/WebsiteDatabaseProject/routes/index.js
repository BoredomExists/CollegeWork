var express = require('express');
var router = express.Router();
var mysql = require('mysql');

/* GET home page. */
router.get('/', function (req, res, next) {
  //console.clear(); // Kept here cause console cluttering sucks

  const sql = 'SELECT * FROM mock_data_billing';
  const headingList = [];
  const dataList = [];

  const connection = mysql.createConnection({
    host: process.env.HOST,
    user: process.env.USER,
    password: process.env.PASSWORD,
    database: process.env.DATABASE
  });

  connection.connect(err => {
    if (err) throw err;
    connection.query(sql, (err, results, fields) => {
      for (let i = 0; i < fields.length; i++) {
        headingList.push(fields[i].name.toUpperCase())
      }
      for (let i = 0; i < results.length; i++) {
        dataList.push(results[i]);
      }
      res.render('billing', {
        title: 'Billing Table',
        HeadingList: headingList,
        DataList: dataList
      });
    });
  });
});

router.post('/insert', (req, res, next) => {
  const connection = mysql.createConnection({
    host: process.env.HOST,
    user: process.env.USER,
    password: process.env.PASSWORD,
    database: process.env.DATABASE
  });

  const { billingID, billingFN, billingLN, apptdate, amtdue, amtpaid, insurance, dateofservice } = req.body;
  const insertSQL = `INSERT INTO mock_data_billing (id, first_name, last_name, Appt_date, Amt_due, Amt_paid, Insurance, date_of_service) VALUES (?, ?, ?, ?, ?, ?, ?, ?)`;
  connection.query(insertSQL, [billingID, billingFN, billingLN, apptdate, amtdue, amtpaid, insurance, dateofservice], (err, results) => {
    if(err)
    {
      console.error(err)
      res.status(500).send('Error inserting message into database');
    } else
    {
      res.redirect('/');
    }
  });
});

module.exports = router;