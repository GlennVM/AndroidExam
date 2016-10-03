var express = require('express')
var pgp = require('pg-promise')()
var db = pgp(`postgres://postgres:1234@localhost:5432/Reason`)
var bodyParser = require('body-parser')
var app = express()
app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())

app.post('/login', function (req, res) {
  clientLogin(req.body.name, req.body.pass).then(function (resp) {
    res.send(resp)
  }).catch(function (err) {
    console.log(err)
  })
})

app.post('/twist', function (req, res) {
  addTwist(req.body.userID, req.body.twistTitle, req.body.twistee, req.body.myAnswer, req.body.twisteeAnswer, req.body.reward).then(function (resp) {
    var response = {
      userID: req.body.userID,
      twistTitle: req.body.twistTitle,
      twistee: req.body.twistee,
      myAnswer: req.body.myAnswer,
      twisteeAnswer: req.body.twisteeAnswer,
      reward: req.body.reward
    }
    res.send(response)
  }).catch(function (err) {
    console.log(err)
  })
})

app.get('/twists/:id', function (req, res) {
  selectAllTwistFromID(req.params.id).then(function (resp) {
    res.send(resp)
  }).catch(function (err) {
    console.log(err)
  })
})

app.post('/won', function (req, res) {
  won(req.body.userID, req.body.twistTitle, req.body.won).then(function (resp) {
    var response = {
      userID: req.body.userID,
      twistTitle: req.body.twistTitle,
      won: req.body.won
    }
    res.send(response)
  }).catch(function (err) {
    console.log(err)
  })
})

function clientLogin (name, pass) {
  return db.one(`SELECT * FROM users WHERE name = '${name}' AND pass = '${pass}'`)
}

function addTwist (userID, twistTitle, twistee, myAnswer, twisteeAnswer, reward) {
  return db.none(`INSERT INTO twists (userid, twisttitle, twistee, myanswer, twisteeanswer, reward) VALUES ('${userID}', '${twistTitle}', '${twistee}', '${myAnswer}', '${twisteeAnswer}', '${reward}');`)
}

function selectAllTwistFromID (userID) {
  return db.any(`SELECT * FROM twists WHERE userid = '${userID}';`)
}

function won (userID, twistTitle, won) {
  return db.none(`UPDATE twists SET won = '${won}' WHERE userid = '${userID}' AND twisttitle = '${twistTitle}';`)
}

app.listen(3000, function () {
  console.log('ReasonAPI listening on port 3000')
})
