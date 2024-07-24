import axios from "axios";


const jwtAxios = axios.create();


const beforeReq = (config) => {

	console.log("beofre request....")
}

const requestFail = (err) => {

	console.log("request error.......")

}

const beforeRes = async (res) => {

	console.log("before return response")

	return res;
}


const responseFail = (err) => {

	console.log("response error.......")

}


jwtAxios.interceptors.request.use(beforeReq, requestFail)

jwtAxios.interceptors.response.use(beforeRes, responseFail)

export default jwtAxios;
