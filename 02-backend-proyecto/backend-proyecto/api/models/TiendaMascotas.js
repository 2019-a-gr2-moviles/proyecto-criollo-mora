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
    fechaNac: {
      type: 'string',
      required: true,
      minLength: 5,
      maxLength: 20,
    },

    //Configuracion del papa
    personaXTienda: { // Nombre atributo de la relacion
      collection: 'perroPorTienda', //Nombre del modelo a relacionar
      via: 'idTiendaXPerro' //Nombre atributo FK del otro modelo
    }


  },

};


