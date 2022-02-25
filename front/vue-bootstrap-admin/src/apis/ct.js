// //
import axios from 'axios'

let url = ''

var findAllData = function () {
  let req = {};
  axios.post(url, req)
    .then(function (response) {
      console.log(response.request.response);
    })
    .catch(function (error) {
      console.log(error);
    });
}
export default {
  findAllData
}