import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {loginPost} from "../api/memberApi";
import {getCookie, removeCookie, setCookie} from "../util/cookieUtil";

const initState = {
	email: ''
}

const loadMemberCookie = () => {
	const memberInfo = getCookie('member')
	console.log(memberInfo)
	return memberInfo;
}

export const loginPostAsync = createAsyncThunk('loginPostAsync', (param) => loginPost(param))

const loginSlice = createSlice({

	name: 'loginSlice',
	initialState: loadMemberCookie() || initState,

	// reducers 금고지기와 같은 개념
	reducers: {
		login: (state, action) => {

			// 리듀서는 파라미터를 두개밖에 못 받음
			// state : 기존상태
			// action : 처리하고 싶은 데이터(= 파라미터)

			console.log("login.....", action)

			console.log(action.payload) // 실데이터

			setCookie("member", JSON.stringify(action.payload), 1)
			return action.payload

			// return {email: action.payload.email}
			// 리턴을 하면 새로운 상태가 됨
		},
		logout: () => {
			console.log("logout.....")

			removeCookie('member')

			return {...initState}
		},
	},

	// createAsyncThunk를 사용했을 때 사용하는 변수
	extraReducers: (builder) => {
		builder.addCase(loginPostAsync.fulfilled, (state, action) => {
			console.log("fulfilled");

			const payload = action.payload


			// 에러가 없을 경우
			if(!payload.error){
				setCookie("member",JSON.stringify(payload))
			}

			return payload



		})
		.addCase(loginPostAsync.pending, (state, action) => {
			console.log("pending");
		})
		.addCase(loginPostAsync.rejected, (state, action) => {
			console.log("rejected");
		})

	}
})

// 외부에서 사용할 수 있도록 함
export const {login, logout} = loginSlice.actions

export default loginSlice.reducer