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
    }
})

app.listen(3000, () => {
    console.log("Listening on port 3000")
})