/**
 * PerroPorPersona.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

//  CONFIGURACION DEL HIJO
    idPersonaXPerro: {  // Nombre del FK para la relacion
      model: 'persona', // Nombre del modelo a relacionar (papa)
      required: true // OPCIONAL -> Siempre ingresar el FK
    },

    idPerroXPersona: {  // Nombre del FK para la relacion
      model: 'perro', // Nombre del modelo a relacionar (papa)
      required: true // OPCIONAL -> Siempre ingresar el FK
    }
  },

};

