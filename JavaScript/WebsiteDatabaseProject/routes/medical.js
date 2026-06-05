var express = require('express');
var router = express.Router();
var mysql = require('mysql');

router.get('/', (req, res, next) => {
    console.clear();

    const sql = 'SELECT * FROM mock_data_med';
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
            res.render('medical', {
                title: 'Medical Table',
                HeadingList: headingList,
                DataList: dataList
            });
        });
    });
});

router.post('/', (req, res, next) => {
    const connection = mysql.createConnection({
      host: process.env.HOST,
      user: process.env.USER,
      password: process.env.PASSWORD,
      database: process.env.DATABASE
    });
  
    const { medicalID, medicalFN, medicalLN, dateprescibed, medname, instructions, quantity, dateofservice } = req.body;
    const insertSQL = `INSERT INTO mock_data_med (id, first_name, last_name, date_prescribed, med_name, instructions, quantity, date_of_service) VALUES (?, ?, ?, ?, ?, ?, ?, ?)`;
    connection.query(insertSQL, [medicalID, medicalFN, medicalLN, dateprescibed, medname, instructions, quantity, dateofservice], (err, results) => {
      if(err)
      {
        console.error(err)
        res.status(500).send('Error inserting message into database');
      } else
      {
        res.redirect('/medical');
      }
    });
});

module.exports = router;