/**
 * RazaPerro.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    nombreRaza: {
      type: 'string',
      required: true,
      minLength: 3,
      maxLength: 30,
    },

    //Configuracion del papa
    razaDePerro: { // Nombre atributo de la relacion
      collection: 'perro', //Nombre del modelo a relacionar
      via: 'idPerro' //Nombre atributo FK del otro modelo
    }




  },

};

