import { createSlice } from "@reduxjs/toolkit";
import { getEmail, getFullName, getUserType } from "../services/token";

const initialState = {
  role: null,
  email: null,
  fullName: null
};
const user = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setRole: (state) => {
      state.role = getUserType();
      state.email = getEmail();
      state.fullName = getFullName();
    }
  }
});

export const { setRole } = user.actions;

export default user.reducer;
