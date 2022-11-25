import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const savedPropertyIds = createSlice({
  name: 'savedPropertyIds',
  initialState: {},
  reducers: {
    setSavedPropertyIds: (state, action) => {
      if (!action.payload || !Array.isArray(action.payload)) {
        state = {};
        return;
      }

      state = action.payload
        .reduce((acc, curr) => Object.assign(acc, { [curr]: true }), {});
    },
    addInSavedPropertyIds: (state, action) => {
      if (action.payload !== null || action.payload !== undefined)
        state[action.payload] = true;
    },
    deleteFromSavedPropertyIds: (state, action) => {
      if (action.payload !== null || action.payload !== undefined)
        state[action.payload] = false;
    }
  }
});

export const { setSavedPropertyIds, addInSavedPropertyIds, deleteFromSavedPropertyIds } = savedPropertyIds.actions;

export const fetchSavedPropertyIds = () => (dispatch) => {
  axios.get("/properties/saved/property-ids")
    .then(res => {
      dispatch(setSavedPropertyIds(res.data));
    })
    .catch(error => {
    });
}

export const deleteFromSavedPropertyIdsAsync = (id, successCallback) => (dispatch) => {
  axios.delete(`/properties/saved/${id}`)
    .then(res => {
      dispatch(deleteFromSavedPropertyIds(id));
      successCallback();
    })
    .catch(error => {
      alert("Failed to remove the property from favorites!");
    });
}

export const addInSavedPropertyIdsAsync = (id) => (dispatch) => {
  axios.post('/properties/saved', { propertyId: id })
    .then(res => {
      dispatch(addInSavedPropertyIds(id));
    })
    .catch(error => {
      alert("Failed to add to favorites list!");
    });
}

export default savedPropertyIds.reducer;
