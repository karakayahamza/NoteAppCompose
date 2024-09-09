package com.example.noteapp.model

import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("quote") var quote: String? = null,
    @SerializedName("author") var author: String? = null,
    @SerializedName("category") var category: String? = null
)


/*

age
alone
amazing
anger
architecture
art
attitude
beauty
best
birthday
business
car
change
communication
computers
cool
courage
dad
dating
death
design
dreams
education
environmental
equality
experience
failure
faith
family
famous
fear
fitness
food
forgiveness
freedom
friendship
funny
future
god
good
government
graduation
great
happiness
health
history
home
hope
humor
imagination
inspirational
intelligence
jealousy
knowledge
leadership
learning
legal
life
love
marriage
medical
men
mom
money
morning
movies
success

 */
