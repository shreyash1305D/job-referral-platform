import { useState } from "react";
import API from "../api/axios";

export default function Register() {

  const [form, setForm] = useState({
    name:"",
    email:"",
    password:"",
    role:"CANDIDATE"
  });

  const signup = async () => {

    await API.post(
      "/users/signup",
      form
    );

    alert("Signup Success");
  };

  return (
    <div>

      <h1>Register</h1>

      <input
        placeholder="Name"
        onChange={(e)=>
          setForm({...form,name:e.target.value})
        }
      />

      <input
        placeholder="Email"
        onChange={(e)=>
          setForm({...form,email:e.target.value})
        }
      />

      <input
        placeholder="Password"
        type="password"
        onChange={(e)=>
          setForm({...form,password:e.target.value})
        }
      />

      <button onClick={signup}>
        Register
      </button>

    </div>
  );
}