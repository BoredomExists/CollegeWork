var express = require('express');
var router = express.Router();
var mysql = require('mysql');

router.get('/', (req, res, next) => {
    console.clear();

    const sql = 'SELECT * FROM mock_data_patient';
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
            res.render('patients', {
                title: 'Patients Table',
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
  
    const { patientsID, patientsFN, patientsLN, phone, address, city, state, zip } = req.body;
    const insertSQL = `INSERT INTO mock_data_patient (id, first_name, last_name, phone, address, city, state, zip_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?)`;
    connection.query(insertSQL, [patientsID, patientsFN, patientsLN, phone, address, city, state, zip], (err, results) => {
      if(err)
      {
        console.error(err)
        res.status(500).send('Error inserting message into database');
      } else
      {
        res.redirect('/patients');
      }
    });
});

module.exports = router;