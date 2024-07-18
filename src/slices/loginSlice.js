import {createSlice} from "@reduxjs/toolkit";

const initState = {
	email : ''
}

const loginSlice = createSlice({

	name : 'loginSlice',
	initialState : initState,

	// reducers 금고지기와 같은 개념
	reducers : {
		login : () => {
			console.log("login.....")
		},
		logout : () => {
			console.log("logout.....")
		}
	}
})

// 외부에서 사용할 수 있도록 함
export const {login, logout} = loginSlice.actions

export default loginSlice.reducer