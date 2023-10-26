const express = require('express');
const app = express();
const PORT = 3000;

app.use('/', express.static(__dirname + '/public'));
app.use('/css', express.static(__dirname + '/node_modules/bootstrap/dist/css/bootstrap.css')); // redirect CSS bootstrap
app.use('/js',  express.static(__dirname + '/node_modules/bootstrap/dist/js/bootstrap.js')); // redirect JS bootstrap
app.use('/jquery',  express.static(__dirname + '/node_modules/jquery/dist/jquery.js')); // redirect JS jquery
app.listen(PORT, () => console.log(`Server listening on port: ${PORT}`));