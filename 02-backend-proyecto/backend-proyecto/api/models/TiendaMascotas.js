/**
 * TiendaMascotas.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    nombre: {
      type: 'string',
      required: true,
      minLength: 3,
      maxLength: 50,
    },
    direccion: {
      type: 'string',
      required: true,
      minLength: 3,
      maxLength: 100,
    },
    telefono: {
      type: 'string',
      required: true,
      minLength: 5,
      maxLength: 20,
    },
    latitud: {
      required: true,
      type: 'string'
    },
    longitud: {
      required: true,
      type: 'string'
    },


    //Configuracion del papa
    perrosXTienda: { // Nombre atr`ibuto de la relacion
      collection: 'perro', //Nombre del modelo a relacionar
      via: 'idTienda' //Nombre atributo FK del otro modelo
    }
/*
    personaXTienda: { // Nombre atributo de la relacion
      collection: 'perroPorTienda', //Nombre del modelo a relacionar
      via: 'idTiendaXPerro' //Nombre atributo FK del otro modelo
    }*/


  },

};


