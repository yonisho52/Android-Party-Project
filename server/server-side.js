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

        app.post('/registerRegularUser', (req, res) => {
            const newUser = {
                firstName:req.body.firstName,
                lastName:req.body.lastName,
                email:req.body.email,
                password:req.body.password,
            }

            //Check if email is unique
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
        })

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
                        email:result.email
                    }
                    res.status(200).send(JSON.stringify(objToSend))
                }
                else
                {
                    res.status(404).send() //Object not found
                }
            })
        })

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
    }
})

app.listen(3000, () => {
    console.log("Listening on port 3000")
})