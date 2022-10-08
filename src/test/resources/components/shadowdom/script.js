(function (){
var root = container.attachShadow( { mode: "open" } )

//Inside element
var h1 = document.createElement( "h1" )
h1.textContent = "Inside Shadow DOM"
h1.id = "inside"
root.appendChild( h1 )

//Access inside element
console.log( container.shadowRoot.querySelector( "#inside" ) )
})()