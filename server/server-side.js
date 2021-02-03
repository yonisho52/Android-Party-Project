const express = require('express')
const app = express()
const mongoClient = require('mongodb').MongoClient

const url = "mongodb://localhost:27017"

app.use(express.json())

mongoClient.connect(url, (err, db) => {
    if(err)
    {
        console.log("Error while connecting mongo client")
    }
    else
    {
        const myDb = db.db('myDb') //If this DB doesnt exist, it will be create automatically
        const collection = myDb.collection('userTable') //Table name
	const eventCollection = myDb.collection('Events')//Events table		


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

        app.post('/login',(req, res) => {
            const query = {
                email:req.body.email,
                password:req.body.password
            }

            collection.findOne(query, (err, result) => {
                if(result != null)
                {
                    const objToSend = 
                    {
                        name:result.name,
                        email:result.email,
                        type:result.type
                    }
                    res.status(200).send(JSON.stringify(objToSend))
                }
                else
                {
                    res.status(404).send() //Object not found
                }
            })
        })

////// kirill

 app.post('/addEvent', (req, res) => {
            const newEvent = {
                CreatedBy:req.body.createdBy,
                Date:req.body.eventDate,
                EventName:req.body.eventName,
                WhosPlaying:req.body.playingDj
            }

            //Check if email is unique
            const query = {CreatedBy:newEvent.CreatedBy, Date:newEvent.Date}
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

        app.post('/getEvents', (req, res) => {
            const query = {
                Date:req.body.eventDate
            }

            eventCollection.findOne(query, (err, result) => {
                if(result != null)
                {
                    const eventDetails = 
                    {
                        createdBy:result.CreatedBy,
                        eventDate:result.Date,
                        eventName:result.EventName,
                        playingDj:result.WhosPlaying    
                    }
                    res.status(200).send(JSON.stringify(eventDetails))
                    //res.status(200).send({createdBy:result.CreatedBy,eventDate:result.Date,playingDj:result.WhosPlaying})
                }
                else
                {
                    res.status(404).send() //Object not found
                }
            })
        })

//// kirill




    }
})

app.listen(3000, () => {
    console.log("Listening on port 3000")
})