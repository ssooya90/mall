import axios from "axios";
import {API_SERVER_HOST} from "./todoApi";


const rest_api_key = '883969be5c1bc3be6c0323bd014734a6'
const redirect_uri = 'http://localhost:3000/member/kakao'

const auth_code_path = `https://kauth.kakao.com/oauth/authorize`

const access_token_url = 'https://kauth.kakao.com/oauth/token'

export const getKakaoLoginLink = () =>{


	const kakaoUrl = `${auth_code_path}?client_id=${rest_api_key}&redirect_uri=${redirect_uri}&response_type=code`

	return kakaoUrl
}

export const getAccessToken = async (authCode) => {

	const header = {headers : {"Content-type" : "application/x-www-form-urlencoded;charset=utf-8"}}

	const params = {
		grant_type : 'authorization_code',
		client_id : rest_api_key,
		redirect_uri : redirect_uri,
		code : authCode,
	}

	const res = await axios.post(access_token_url, params, header)

	const accessToken = res.data.access_token

	return accessToken


}


export const getMemberWithAccessToken = async (accessToken) => {

	console.log("HERE")
	console.log(accessToken)

	const res = axios.get(`${API_SERVER_HOST}/api/member/kakao?accessToken=${accessToken}`)

	return res.data;

}