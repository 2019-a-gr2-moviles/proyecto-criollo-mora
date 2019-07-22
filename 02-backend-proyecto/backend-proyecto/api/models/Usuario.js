/**
 * Usuario.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    username: {
      type: 'string',
      required: true,
      unique: true,
      minLength: 3,
      maxLength: 60,
    },
    password: {
      type: 'string',
      required: true,
      minLength: 5,
      maxLength: 10,
    },
    tipo: {
      type: 'string',
      enum: ['administrador', 'cliente']
    }//,

    /*
    //  CONFIGURACION DEL HIJO
    fkEmpresa: {  // Nombre del FK para la relacion
      model: 'empresa', // Nombre del modelo a relacionar (papa)
      // required: true // OPCIONAL -> Siempre ingresar el FK
    },*/
/*
    //Configuracion del papa
    serviciosDeUsuario: { // Nombre atributo de la relacion
      collection: 'servicio', //Nombre del modelo a relacionar
      via: 'fkUsuario' //Nombre atributo FK del otro modelo
    }*/


  },



};

