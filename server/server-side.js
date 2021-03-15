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
        const messageCollection = client.db("myDb").collection("adsMessages");
        const surevyCollection = client.db("myDb").collection("survey");
        
        //   const myDb = db.db('myDb') //If this DB doesnt exist, it will be create automatically
        //   const collection = myDb.collection('userTable') //Table name
        //   const eventCollection = myDb.collection('Events')//Events table		


        app.post('/rateDjOrPlaceByEmail',(req, res) => {
            const query = ({ email:req.body.email })
            const rate = ({ rating:req.body.rating })
            var average
            collection.findOne(query, (err, result) => {               
                if(result!=null)
                {
                    var rating = parseFloat(result.rating)
                    var numOfRates = parseInt(result.numOfRates,10)
                    
                    if(numOfRates==0)
                        average = rate.rating;
                    else if(rating ==0.0 && rate.rating ==0)
                        average = 0.0;
                    else
                        average = (rating*numOfRates + parseFloat(rate.rating))/(numOfRates+1)
                    numOfRates++

                    collection.updateOne(query,
                        {$set: {rating: average, numOfRates: numOfRates }},
                        (err, result) => {
                            if(!err)
                                res.status(200).send()
                            else
                                res.status(400).send()
                        })
                }   
                else
                    res.status(400).send(err);
        })
    })

    app.post('/rateEventByPartyCode',(req, res) => {
        const query = ({ partyCode:req.body.partyCode })
        const rate = ({ rating:req.body.rating })
        var average
        eventCollection.findOne(query, (err, result) => {               
            if(result!=null)
            {
                var rating = parseFloat(result.eventRating)
                var numOfRates = parseInt(result.numOfRates,10)
                
                if(numOfRates==0)
                    average = rate.rating;
                else if(rating ==0.0 && rate.rating ==0)
                    average = 0.0;
                else
                    average = (rating*numOfRates + parseFloat(rate.rating))/(numOfRates+1)
                numOfRates++

                eventCollection.updateOne(query,
                    {$: {eventRating: average, numOfRates: numOfRates }},
                    (err, result) => {
                        if(!err)
                            res.status(200).send()
                        else
                            res.status(400).send()
                    })
            }   
            else
                res.status(400).send(err);
        })
    })

    app.post("/deleteEvent", (req, res) => {
        const partyCode = ({partyCode:req.body.partyCode})

            eventCollection.deleteOne({partyCode:req.body.partyCode}, (req,result) => {
                if(!err)
                    res.status(200).send()
                else
                    res.status(400).send()
            })
            
        })

    



    app.post('/addSurvey',(req, res) => {
        const addSurevy = ({ 
            partyCode:req.body.partyCode,
            question: req.body.question,
            ans1: req.body.ans1,
            ans1Rate: req.body.ans1Rate,
            ans1NumOfRate: req.body.ans1NumOfRate,
            ans2: req.body.ans2,
            ans2Rate: req.body.ans2Rate,
            ans2NumOfRate: req.body.ans2NumOfRate,
            ans3: req.body.ans3,
            ans3Rate: req.body.ans3Rate,
            ans3NumOfRate: req.body.ans3NumOfRate
        })
        surevyCollection.findOne({partyCode:addSurevy.partyCode}, (err, result) => {               
            if(result==null)
            {
                surevyCollection.insertOne(addSurevy, (err, result) => {
                        if(!err)
                            res.status(200).send()
                        else
                            res.status(400).send(err)
                    })
            }   
            else
                res.status(400).send(err);
        })
    })

        app.post('/getSurveyByPartyCode',(req, res) => {
            const query = ({partyCode:req.body.partyCode})
            surevyCollection.findOne(query, (err, result) => {               
            {
                if(result!=null)
                    res.status(200).send(JSON.stringify(result))
                else
                    res.status(400).send(err);
            }
        })
    })

        app.post('/voteForSurvey',(req, res) => {
            const query = ({
                partyCode:req.body.partyCode,
                ansNumber: req.body.ansNumber // ***ansNumber = ans1/ans2/ans3***
            })
            surevyCollection.findOne({partyCode:query.partyCode}, (err, result) => {               
            {
                if(result!=null)
                {
                    var newAverage1,newAverage2,newAverage3;
                    var newNumOfRate1 = parseInt(result.ans1NumOfRate),
                    newNumOfRate2 = parseInt(result.ans2NumOfRate),
                    newNumOfRate3 = parseInt(result.ans3NumOfRate);
                    var totalNumOfRates = newNumOfRate1+newNumOfRate2+newNumOfRate3 + 1;

                    if(query.ansNumber == "ans1")
                    {
                        newAverage1 = (parseInt(result.ans1NumOfRate)+1)/totalNumOfRates*100;
                        newAverage2 = (parseInt(result.ans2NumOfRate))/totalNumOfRates*100;
                        newAverage3 = (parseInt(result.ans3NumOfRate))/totalNumOfRates*100;
                        newNumOfRate1++;
                    }
                    else if(query.ansNumber == "ans2")
                    {
                        newAverage1 = (parseInt(result.ans1NumOfRate))/totalNumOfRates*100;
                        newAverage2 = (parseInt(result.ans2NumOfRate)+1)/totalNumOfRates*100;
                        newAverage3 = (parseInt(result.ans3NumOfRate))/totalNumOfRates*100;
                        newNumOfRate2++;
                    }
                    else
                    {
                        newAverage1 = (parseInt(result.ans1NumOfRate))/totalNumOfRates*100;
                        newAverage2 = (parseInt(result.ans2NumOfRate))/totalNumOfRates*100;
                        newAverage3 = (parseInt(result.ans3NumOfRate)+1)/totalNumOfRates*100;
                        newNumOfRate3++;
                    }
                
                    surevyCollection.updateOne({partyCode:query.partyCode},
                        {$set: {
                            ans1Rate: newAverage1, 
                            ans1NumOfRate: newNumOfRate1,
                            ans2Rate: newAverage2, 
                            ans2NumOfRate: newNumOfRate2,
                            ans3Rate: newAverage3, 
                            ans3NumOfRate: newNumOfRate3,
                         }},
                        (err, result) => {
                            if(!err)
                                res.status(200).send()
                            else
                                res.status(400).send()
                        })
                }
                else
                    res.status(400).send(err);
            }
        })
    })


        app.post('/addMessageToAds', (req, res) => {
            const newAds = {
                createdBy:req.body.createdBy,
                date:req.body.date,
                time:req.body.time,
                text:req.body.text,
            }
            messageCollection.insertOne(newAds, (err, result) => {
                if(!err)
                    res.status(200).send() //If insertion was successful
                else
                    res.status(err).send()
                })
            })

        app.get('/getAllMessages',(req, res) => {
            
            messageCollection.find().toArray(function(err, result)
            {
                if(err)
                    res.send(err);
                if(result==null)
                    res.status(400).send("there is no DJ registered")
                else
                {
                    res.status(200).send(JSON.stringify(result))
                }
            }
            )
        })


        app.post('/getPlaceNameOrStageNameByEmail',(req, res) => {
            const query = ({email:req.body.email})
            collection.findOne(query, (err, result) => {               
            {
                if(result.stageName!=null)
                    res.status(200).send(JSON.stringify(result.stageName))
                else if(result.placeName!=null)
                {
                    res.status(200).send((result.placeName))
                }
                else
                    res.status(400).send(err);
            }
        })
    })

  
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
                  rating:req.body.rating,
                  numOfRates:req.body.numOfRates
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
                  rating:req.body.rating,
                  numOfRates:req.body.numOfRates
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
          app.post('/getDjEmailByStageName',(req, res) => {
  
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
                      res.status(200).send(dj.email)
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
              const query = {
                  email:req.body.email,
                  password:req.body.password
              }
  
              collection.findOne(query, (err, result) => {
                  if(result != null)
                  {
                    let objToSend = null
                    if(result.type=="DJ")
                    {
                        objToSend = 
                      {
                          email:result.email,
                          type:result.type,
                          stageName:result.stageName
                      }
                    }
                    else if(result.type=="REGULAR")
                    {
                        objToSend = 
                        {
                            email:result.email,
                            type:result.type,
                        }
                    }
                    else if(result.type=="PLACE-OWNER")
                    {
                        objToSend = 
                        {
                            email:result.email,
                            type:result.type,
                            placeName:result.placeName
                        }
                    }
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
                  partyCode:req.body.partyCode,
                  createdBy:req.body.createdBy,
                  placeName:req.body.placeName,
                  eventDate:req.body.eventDate,
                  eventName:req.body.eventName,
                  whosPlayingName:req.body.whosPlayingName,
                  whosPlaying:req.body.whosPlaying,
                  startTime:req.body.startTime,
                  endTime:req.body.endTime,
                  eventRating:req.body.eventRating,
                  numOfRates:req.body.numOfRates
              }
  
              //Check if email is unique
              const query = {createdBy:newEvent.createdBy, eventDate:newEvent.eventDate}
              eventCollection.findOne(query, (err, result) =>
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
                  eventDate:req.body.Date
              }
              eventCollection.find(query).toArray(function (err, result) {
                  if(result.length!=0)
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
                              whosPlaying:result.email
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
  
                  else if(result.type=="PLACE-OWNER")
                  {
                      collection.findOne(query, (err, result) => {
                          const query = {
                              createdBy:result.email
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
          });

          app.post('/getEventByDateAndEmail',(req, res) => {
            const queryOwner = {
                createdBy:req.body.email,
                eventDate:req.body.date
            }
            const queryDj = {
                whosPlaying:req.body.email,
                eventDate:req.body.date
            }
            eventCollection.findOne(queryOwner, (err, result) =>
            {
                if(result != null)
                {
                    res.status(200).send(JSON.stringify(result))
                }
                else
                {
                    eventCollection.findOne(queryDj, (err, result) =>
                    {
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
        })

        app.post('/checkPartyNowByCodeAndDate',(req, res) => {
            const query = {
                partyCode:req.body.partyCode,
                eventDate:req.body.eventDate
            }
            eventCollection.findOne(query, (err, result) =>
            {
                if(result != null)
                {
                    res.status(200).send(JSON.stringify(result))
                }
                else
                {
                    res.status(400).send() //Object not found
                }
            }
            )
        })

        app.post('/addSavedEvent',(req, res) => {
            const query = {
                email:req.body.email
            }
            const code = {
                partyCode:req.body.partyCode
            }
            collection.findOne({email:req.body.email,savedEvent:req.body.partyCode}, (req,res1) => {
                if(res1!=null)
                {
                    res.status(400).send("The Event Already In !")
                }
                else
                {
                    collection.updateOne({email:query.email},
                        {$push: {savedEvent: code.partyCode}}, 
                        (err, result) => {
                            if(!err)
                                res.status(200).send()
                            else
                                res.status(400).send()
                        })
                }
            })
        })

        app.post('/getSavedEvents', (req ,res) => {

            const query = { email:req.body.email}
            var savedEventArray = new Array()
            collection.findOne(query, (req,result) => {
                if(result != null)
                {
                    var arr = result.savedEvent 
                    if(arr.length==0)
                        res.status(400).send("Empty")

                    var promise = new Promise((resolve,rejects) => {
                        arr.forEach( (element) => {
                            eventCollection.findOne({partyCode:element}, (req,res1) => {
                                if(res1!=null)
                                {
                                    savedEventArray.push(res1)
                                }
                                if(arr[arr.length-1]==element) 
                                    resolve();
                            })
                        });
                    })
                    promise.then(() => {
                        res.status(200).send(JSON.stringify(savedEventArray))
                    })
                }
                else
                {
                    res.status(400).send("Empty")
                }
                
            })
        })

        app.post('/checkIfEventSaved',(req, res) => {
            const query = {
                email:req.body.email
            }
            const code = {
                partyCode:req.body.partyCode
            }
            collection.findOne({email:req.body.email,savedEvent:req.body.partyCode}, (req,res1) => {
                if(res1!=null)
                {
                    res.status(200).send()
                }
                else
                {
                    res.status(400).send()
                }
            })
        })

        app.post('/removeSavedEvents', (req ,res) => {

            const queryMail = { email:req.body.email[0]}
            const queryCode = { partyCode:req.body.partyCode }
            var savedEventArray = new Array()
            //var l =queryMail.email[0]
            var valid =true
            if(queryCode.partyCode.length==0)
                valid = false
            collection.findOne(queryMail, (req,result) => {
                
                if(result != null && valid)
                {
                    var arr = result.savedEvent 
                    var promise = new Promise((resolve,rejects) => {

                        for(let i=0; i<arr.length; i++)
                        {
                            var a = arr[i]
                            for(let j=0; j<queryCode.partyCode.length;j++)
                            {
                                var b = queryCode.partyCode[j]
                                if(arr[i] == queryCode.partyCode[j])
                                {
                                    collection.updateOne(queryMail,{$pull : {savedEvent:arr[i]}}, (req,result) => {
                                        if(req)
                                            res.status(400).send(req)
                                    })
                                }
                            }
                            if(i==arr.length-1)
                            {
                                resolve();
                            }
                        }
                    })
                    
                    promise.then(() => {
                        res.status(200).send()
                        try{
                        reject();
                        }
                        catch{
                            res.status(400).send()
                        }
                    })
                }
                else
                {
                    res.status(400).send("Empty")
                }   
            })
        })

        
  
  app.listen(3000, () => {
      console.log("Listening on port 3000")
  })

  //client.close();
});
