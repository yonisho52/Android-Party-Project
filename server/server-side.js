const express = require('express')
const app = express()
const MongoClient = require('mongodb').MongoClient

//const url = "mongodb://localhost:27017"
//const url = "cluster-android-project-shard-00-02.pykq3.mongodb.net:27017"
//const url = "mongodb+srv://cluster-android-project-shard-00-00.pykq3.mongodb.net:27017/?poolSize=20&writeConcern=majority"

//const url = "mongodb+srv://admin:4321@cluster-android-project.pykq3.mongodb.net/Cluster-Android-Project?retryWrites=true&w=majority"
const url = "mongodb+srv://admin:4321@cluster-android-project.pykq3.mongodb.net/myDb?retryWrites=true&w=majority"
const client = new MongoClient(url, { useNewUrlParser: true });
client.connect(err => {

  // perform actions on the collection object
  app.use(express.json())

        const collection = client.db("myDb").collection("userTable");
        const eventCollection = client.db("myDb").collection("Events");
        
        //   const myDb = db.db('myDb') //If this DB doesnt exist, it will be create automatically
        //   const collection = myDb.collection('userTable') //Table name
        //   const eventCollection = myDb.collection('Events')//Events table		
  
          // START REGISTER REGULAR
          app.post('/registerRegularUser', (req, res) => {
              const newUser = {
                  type:req.body.type,
                  email:req.body.email,
                  firstName:req.body.firstName,
                  lastName:req.body.lastName,
                  password:req.body.password,
                  favouriteGenres:req.body.favouriteGenres,
                  age:req.body.age
              }
              
              checkRegister(newUser,req,res)
          })
          // END REGISTER REGULAR
          
          // START REGISTER DJ
          app.post('/registerDjUser', (req, res) => {
              const newUser = {
                  type:req.body.type,
                  email:req.body.email,
                  firstName:req.body.firstName,
                  lastName:req.body.lastName,
                  password:req.body.password,
                  stageName:req.body.stageName,
                  playingGenre:req.body.playingGenre,
                  youtubeLink:req.body.youtubeLink,
                  spotifyLink:req.body.spotifyLink,
                  appleMusicLink:req.body.appleMusicLink,
                  age:req.body.age,
                  placesCanBeFound:req.body.placesCanBeFound,
                  rating:req.body.rating
              }
  
              checkRegister(newUser,req,res)
          })
          // END REGISTER PLACE OWNER
  
  
          // START REGISTER PLACE OWNER
          app.post('/registerPlaceOwnerUser', (req, res) => {
              const newUser = {
                  type:req.body.type,
                  email:req.body.email,
                  firstName:req.body.firstName,
                  lastName:req.body.lastName,
                  password:req.body.password,
                  placeName:req.body.placeName,
                  placeType:req.body.placeType,
                  placeAddress:req.body.placeAddress,
                  rating:req.body.rating
              }
  
              checkRegister(newUser,req,res)
          })
          // END REGISTER PLACE OWNER
  
  
          ///NEWW
          app.post('/checkDjName', (req, res) => {
              const checkDjName = {
                  stageName:req.body.stageName
              }
  
              const query = {stageName:checkDjName.stageName}
              collection.findOne(query, (err, result) =>
              {
                  if(result==null)
                  {
                      res.status(200).send() //If insertion was successful  
                  }
                  else{
                      res.status(400).send() //Bad request, email already exists
                  }
              })
          })
  
          app.post('/checkPlaceName', (req, res) => {
              const checkPlaceName = {
                  placeName:req.body.placeName
              }
  
              const query = {placeName:checkPlaceName.placeName}
              collection.findOne(query, (err, result) =>
              {
                  if(result==null)
                  {
                      res.status(200).send() //If insertion was successful  
                  }
                  else{
                      res.status(400).send() //Bad request, email already exists
                  }
              })
          })
  
          
          app.get('/getAllDjStageName',(req, res) => {
              
              collection.find({type:"DJ"}).toArray(function(err, dj)
              {
                  if(err)
                      res.send(err);
                  if(dj==null)
                      res.status(400).send("there is no DJ registered")
                  else
                  {
                      res.status(200).send(JSON.stringify(dj))
                  }
              }
              )
          })
  
          app.get('/getDjEmailByStageName',(req, res) => {
  
              const query = {
                  stageName:req.body.stageName
              }
              
              collection.findOne((query), (err, dj) =>
              {
                  if(err)
                      res.send(err);
                  if(dj==null)
                      res.status(400).send("there is no DJ registered")
                  else
                  {
                      res.status(200).send(JSON.stringify(dj.email))
                  }
              })
          })
  
          app.post('/getUserByEmail',(req, res) => {
              const query = {
                  email:req.body.email
              }
              collection.findOne(query, (err, result) =>
              {
                  if(result != null)
                  {
                      //console.log(JSON.stringify(result))
                      res.status(200).send(JSON.stringify(result))
                  }
                  else
                  {
                      res.status(404).send() //Object not found
                  }
              }
              )
          })
  
          app.post('/login',(req, res) => {
            console.log("connect")
              const query = {
                  email:req.body.email,
                  password:req.body.password
              }
  
              collection.findOne(query,{password:false}, (err, result) => {
                  if(result != null)
                  {
                      const objToSend = 
                      {
                          email:result.email,
                          type:result.type,
                      }
                      //console.log(JSON.stringify(objToSend))
                      res.status(200).send(JSON.stringify(objToSend))
                  }
                  else
                  {
                      res.status(400).send() //Object not found
                  }
              })
          })
  
  
  
  /////// START check register and ***SEND*** result
          function checkRegister(newUser,req,res)
          {
              const query = {email:newUser.email}
              collection.findOne(query, (err, result) =>
              {
                  if(result==null)
                  {
                      collection.insertOne(newUser, (err, result) => {
                          res.status(200).send() //If insertion was successful
                      })
                  }
                  else{
                      res.status(400).send() //Bad request, email already exists
                  }
              })
          }
  /////// END check register and ***SEND*** result
  
  
  
  ////// kirill
  
   app.post('/addEvent', (req, res) => {
              const newEvent = {
                  CreatedBy:req.body.createdBy,
                  Date:req.body.eventDate,
                  EventName:req.body.eventName,
                  WhosPlaying:req.body.playingDj,
                  StartTime:req.body.startTime,
                  Endtime:req.body.endTime
              }
  
              //Check if email is unique
              const query = {CreatedBy:newEvent.CreatedBy, Date:newEvent.Date}
              eventCollection.find(query, (err, result) =>
              {
                  if(result==null)
                  {
                      eventCollection.insertOne(newEvent, (err, result) => {
                          res.status(200).send() //If insertion was successful
                      })
                  }
                  else{
                      res.status(400).send() //Bad request, event already exists
                  }
              })
          })
  
          app.post('/getEventsByDate', (req, res) => {
              const query = {
                  Date:req.body.eventDate
              }
              eventCollection.find(query).toArray(function (err, result) {
                  if(result != null)
                  {
                      // const eventDetails = 
                      // {
                      //     createdBy:result.CreatedBy,
                      //     eventDate:result.Date,
                      //     eventName:result.EventName,
                      //     playingDj:result.WhosPlaying,
                      //     partyCode:result.PartyCode    
                      // }
                      res.status(200).send(JSON.stringify(result))
                      //res.status(200).send({createdBy:result.CreatedBy,eventDate:result.Date,playingDj:result.WhosPlaying})
                  }
                  else
                  {
                      res.status(400).send() //Object not found
                  }
              })
          })
  
          app.post('/getEventsByEmail', (req, res) => {
              const query = {
                  email:req.body.email,
              }
  
              collection.findOne(query, (err, result) => {
                  if(result == null)
                  {
                      res.status(400).send()
                  }
  
                  else if(result.type=="DJ")
                  {
                      collection.findOne(query, (err, result) => {
                          const query = {
                              WhosPlaying:result.email
                          }
  
                          if(result==null)
                          {
                              res.status(400).send();
                          }
                          else
                          {
                              eventCollection.find({result:stageName}).toArray(function (err, result) {
                                  if(result != null)
                                  {
                                      res.status(200).send(JSON.stringify(result))
                                  }
                                  else
                                  {
                                      res.status(400).send() //Object not found
                                  }
                              })
                          }
                      })
                  }
  
                  else if(result.type=="PLACE-OWNER")
                  {
                      collection.findOne(query, (err, result) => {
                          const query = {
                              CreatedBy:result.email
                          }
  
                          if(result==null)
                          {
                              res.status(400).send();
                          }
                          else
                          {
                              eventCollection.find(query).toArray(function (err, result) {
                                  if(result != null)
                                  {
                                      res.status(200).send(JSON.stringify(result))
                                  }
                                  else
                                  {
                                      res.status(400).send() //Object not found
                                  }
                              })
                          }
                      })
                  }
          })
      })
  
  
          app.post('/checkValidPartyCode',(req, res) => {
              const query = {
                  partyCode:req.body.partyCode
              }
              eventCollection.findOne(query, (err, result) =>
              {
                  if(result == null)
                  {
                      res.status(200).send()
                  }
                  else
                  {
                      res.status(400).send() //Object not found
                  }
              }
              )
          })
  
  
  app.listen(3000, () => {
      console.log("Listening on port 3000")
  })




  //client.close();
});
