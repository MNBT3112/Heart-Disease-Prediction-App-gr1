import numpy as np
from flask import Flask, request, jsonify
import pickle
from flask_cors import CORS

app = Flask(__name__)
CORS(app)  # Enable Cross-Origin Resource Sharing so that your Android app can access the API

# Load the trained model (ensure that model1.pkl exists in the same folder)
model = pickle.load(open('model1.pkl', 'rb'))

@app.route('/')
def home():
    return "Heart Disease Prediction App"

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Retrieve input values from the POST form data
        cp = int(request.form.get('cp'))
        thalach = int(request.form.get('thalach'))
        slope = int(request.form.get('slope'))
        restecg = int(request.form.get('restecg'))
        chol = float(request.form.get('chol'))
        trestbps = float(request.form.get('trestbps'))
        fbs = int(request.form.get('fbs'))
        oldpeak = float(request.form.get('oldpeak'))

        # Build the input feature array (2D array for scikit-learn)
        input_data = np.array([[cp, thalach, slope, restecg, chol, trestbps, fbs, oldpeak]])
        
        # Get class prediction and prediction confidence probabilities
        prediction = model.predict(input_data)[0]
        probabilities = model.predict_proba(input_data)[0]
        confidence = probabilities[prediction] * 100  # converting to percentage

        # Prepare a detailed response, adjusting the description as needed
        response = {
            'heart_disease_prediction': str(prediction),  # "1" means disease likely, "0" means none
            'confidence_percentage': f"{confidence:.2f}%",
            'description': 'Heart disease likely' if prediction == 1 else 'No heart disease detected'
        }
        
        return jsonify(response)
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)