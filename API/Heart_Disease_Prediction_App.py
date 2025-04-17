from flask import Flask, request, jsonify
import random

app = Flask(__name__)

@app.route('/', methods=['GET'])
def home():
    return "Heart Disease Prediction API Running!"

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Retrieve form parameters
        cp = request.form.get('cp', None)
        thalach = request.form.get('thalach', None)
        slope = request.form.get('slope', None)
        restecg = request.form.get('restecg', None)
        chol = request.form.get('chol', None)
        trestbps = request.form.get('trestbps', None)
        fbs = request.form.get('fbs', None)
        oldpeak = request.form.get('oldpeak', None)
        
        # Validate that none of the parameters are missing
        if None in [cp, thalach, slope, restecg, chol, trestbps, fbs, oldpeak]:
            return jsonify({"error": "Missing one or more required parameters."}), 400
        
        # Convert parameters to the appropriate types
        cp = int(cp)
        thalach = int(thalach)
        slope = int(slope)
        restecg = int(restecg)
        chol = float(chol)
        trestbps = float(trestbps)
        fbs = int(fbs)
        oldpeak = float(oldpeak)
        
        # Validate critical input ranges
        if chol < 100 or chol > 300:
            return jsonify({"error": "Cholesterol value out of range (100-300 mg/dL)."}), 400
        if thalach < 60 or thalach > 220:
            return jsonify({"error": "Max Heart Rate value out of range (60-220 bpm)."}), 400
        
        # Fake prediction logic using the cholesterol and max heart rate values
        if chol > 250 and thalach > 150:
            fake_percentage = random.randint(90, 100)
            outcome = "Very High Chances of Heart Disease"
        elif chol > 150:
            fake_percentage = random.randint(80, 90)
            outcome = "Chances of Heart Disease"
        else:
            fake_percentage = random.randint(70, 80)
            outcome = "Chances of No Heart Disease"
        
        # Build and return the JSON response
        response = {
            "heart_disease_prediction": outcome,
            "confidence_percentage": f"{fake_percentage}%"
        }
        return jsonify(response)
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)